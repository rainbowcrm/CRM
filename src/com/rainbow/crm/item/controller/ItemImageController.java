package com.rainbow.crm.item.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.model.ItemImageSet;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.item.service.ItemService;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class ItemImageController extends GeneralController{

	ItemImageSet imageSet ;
	List<ItemImage>  images ;
	ServletContext ctx  = null ;
	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}
	
	@Override
	public PageResult save(ModelObject object) {
		imageSet = (ItemImageSet) object;
		images = splitImageSet(imageSet,(CRMContext)getContext());
		if (!Utils.isNullList(images)) {
			for(ItemImage image :  images) {
				saveFile(image.getImage(), image.getFilePath(), image.getFileName());
				if(image.getFileName().charAt(image.getFileName().length()-1) != '.' ) {
				ftpAPFile(image.getImage(), image.getFilePath(), image.getFileName(), (CRMContext)getContext());
				//ftpJCSFile(image.getImage(), image.getFilePath(), image.getFileName(), (CRMContext)getContext());
				}
			}
			saveRecords();
		}
		return new PageResult();
	}
	@Override
	public PageResult delete(ModelObject object) {
		imageSet = (ItemImageSet) object;
		ItemService service = (ItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService") ;
		Item item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), imageSet.getItem().getName());
		ItemImageSQL.DeleteAllImagesforItem(item);
		imageSet.setFilewithPath1("");
		imageSet.setFilewithPath2("");
		imageSet.setFilewithPath3("");
		return new PageResult();
	}

	@Override
	public PageResult read(ModelObject object) {
		imageSet = (ItemImageSet) object;
		try {
			IItemService service = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService") ;
			Item item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), imageSet.getItem().getName());
			String filePath = CRMAppConfig.INSTANCE.getProperty("Image_Path");
			String code = ((CRMContext) getContext()).getLoggedinCompanyCode();
			ItemImage dbRecord1 = ItemImageSQL.getItemImage(item.getId(), 'a');
			if (dbRecord1 != null ) {
				imageSet.setFilewithPath1(filePath + "\\" +  code  + "\\" + dbRecord1.getFileName());
			}
			ItemImage dbRecord2 = ItemImageSQL.getItemImage(item.getId(), 'b');
			if (dbRecord2 != null ) {
				imageSet.setFilewithPath2(filePath + "\\" +  code  + "\\" + dbRecord2.getFileName());
			}
			ItemImage dbRecord3 = ItemImageSQL.getItemImage(item.getId(), 'c');
			if (dbRecord3 != null ) {
				imageSet.setFilewithPath3(filePath + "\\" +  code  + "\\" + dbRecord3.getFileName());
			}
		}catch(Exception ex) {
			
		}
		return new PageResult();

	}



	private void saveRecords() {
		for(ItemImage image :  images) {
			ItemImage dbRecord = ItemImageSQL.getItemImage(image.getItem().getId(), image.getSuffix());
			if(dbRecord == null  ) {
				ItemImageSQL.insertItemImage(image,(CRMContext) getContext());
			}else {
				dbRecord.setFileName(image.getFileName());
				ItemImageSQL.updateItemImage(dbRecord,(CRMContext) getContext());
			}
		
		}
	}

	
	
	private void ftpJCSFile (byte[] bytes, String filepath, String fileName,
			CRMContext context) 
	{
		try {
		JSch jsch = new JSch();
		/* String privateKey = "~/.ssh/id_rsa";
		 jsch.addIdentity(privateKey);*/ 
		String host = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_HOST, context);
		String user = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_USER, context);
		String pass = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_PASSWORD, context);
		String port = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_PORT, context);
	    Session session = jsch.getSession( user, host, Integer.parseInt(port) );
	    session.setConfig( "PreferredAuthentications", "password" );
	    session.setConfig("StrictHostKeyChecking", "no"); 
	    session.setPassword( pass );
	    session.connect(10000);
	    Channel channel = session.openChannel( "sftp" );
	    ChannelSftp sftp = ( ChannelSftp ) channel;
	    sftp.connect( 10000 );
	    ByteArrayInputStream inputStream = new ByteArrayInputStream(
				bytes);
	    sftp.put(inputStream,fileName);
	    sftp.quit();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void ftpAPFile(byte[] bytes, String filepath, String fileName,
			CRMContext context) {
		if (Utils.isNullString(fileName))
			return;
		String host = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_HOST, context);
		String user = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_USER, context);
		String pass = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_PASSWORD, context);
		String port = ConfigurationManager.getConfig(
				ConfigurationManager.IMAGE_SERVER_PORT, context);
		FTPClient  ftpClient = new FTPClient ();
		try {
			ftpClient.setDefaultTimeout(100000);
			if (Integer.parseInt(port) > 0)
				ftpClient.connect(host,Integer.parseInt(port));
			else
				ftpClient.connect(host);
			ftpClient.enterLocalPassiveMode();
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				
				ftpClient.login(user, pass);
				//FTPFile[] files = ftpClient.listFiles(directory);
				 
				//ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						bytes);
				System.out.println("Start uploading first file");
				boolean done = ftpClient.storeFile(fileName, inputStream);
				inputStream.close();
				InputStream st2 = ftpClient.retrieveFileStream("/public_html/pics/MP003-a.jpg");
				if (done) {
					System.out
							.println("The first file is uploaded successfully.");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void ftpFile (byte [] bytes , String filepath, String fileName, CRMContext context) {
		String ftpUrl = "ftp://%s:%s@%s;type=i";
		if (Utils.isNullString(fileName)) return ;
		String host = ConfigurationManager.getConfig(ConfigurationManager.IMAGE_SERVER_HOST, context);
		String user = ConfigurationManager.getConfig(ConfigurationManager.IMAGE_SERVER_USER, context);
		String pass = ConfigurationManager.getConfig(ConfigurationManager.IMAGE_SERVER_PASSWORD, context);
		String uploadPath = fileName;
		
		ftpUrl = String.format(ftpUrl, user, pass, host);
		System.out.println("Upload URL: " + ftpUrl);

		try {
		URL url = new URL(ftpUrl);
		URLConnection conn = url.openConnection();
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(bytes);
		outputStream.close();
		   System.out.println("File uploaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	private void saveFile(byte [] bytes , String filepath, String fileName) {
		try {
		String folderPath = 	ctx.getRealPath("." ) + "//" + filepath ;
		File folder = new  File(folderPath);
		if(!folder.exists()) {
			boolean b = folder.mkdir();
			System.out.println("b=" + b);
			
		}
		FileOutputStream fos = new FileOutputStream(folderPath + "//" + fileName);
		fos.write(bytes);
		fos.close(); 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
	}
	
	private String getFileExtn(String fullName) {
		if (fullName.contains(".")) {
			String laterPart = fullName.substring(fullName.indexOf(".")+1,fullName.length());
			return laterPart;
		}
		return "";
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response) {
		ctx =  request.getServletContext() ;
		return LoginSQLs.loggedInUser(request.getSession().getId());
		
	}
	
	@Override
	public IRadsContext generateContext(String authToken) {
		return LoginSQLs.loggedInUser(authToken);
	}
	
	private List<ItemImage> splitImageSet(ItemImageSet set,CRMContext context) {
		
		List<ItemImage> images = new ArrayList<ItemImage> ();
		IItemService service = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService") ;
		Item item = service.getByName(((CRMContext) getContext()).getLoggedinCompany(), set.getItem().getName());
		try  {
			String filePath = CRMAppConfig.INSTANCE.getProperty("Image_Path");
			String code = context.getLoggedinCompanyCode();
			if(set.getImage1() != null ) {
				ItemImage image = new ItemImage();
				image.setItem(item);
				image.setImage(set.getImage1());
				image.setSuffix('a');
				image.setFilePath(filePath + "\\" +  code );
				image.setFileName( set.getItem().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName1()) ); 
				images.add(image);
				set.setFilewithPath1(image.getFilePath() + "\\" + image.getFileName());
			}
			if(set.getImage2() != null ) {
				ItemImage image = new ItemImage();
				image.setItem(item);
				image.setImage(set.getImage2());
				image.setSuffix('b');
				image.setFilePath(filePath + "\\" +  code );
				image.setFileName( set.getItem().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName2()) );
				images.add(image);
				set.setFilewithPath2(image.getFilePath() + "\\" + image.getFileName());
			}
			if(set.getImage3() != null ) {
				ItemImage image = new ItemImage();
				image.setItem(item);
				image.setImage(set.getImage3());
				image.setSuffix('c');
				image.setFilePath(filePath + "\\" +  code );
				image.setFileName( set.getItem().getCode() + "-" + image.getSuffix() + "." + getFileExtn(set.getFileName3()) );
				images.add(image);
				set.setFilewithPath3(image.getFilePath() + "\\" + image.getFileName());
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return images;
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	
}
