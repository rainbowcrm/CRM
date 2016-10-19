export class Login{
    username:string;
    password:string
}

export class LoginRequest{
    pageID:string;
    dataObject:Login;
}

export class LoginResponse{
    Username: string;
    Password: string;
}