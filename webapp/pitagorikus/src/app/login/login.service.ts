import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from './model/Auth';
import { Register } from './model/Register';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  
  constructor(private http: HttpClient){

  }
  
  //  .../api/auth/login
  public login(auth: Auth){

  }

  //  .../api/user/register
  public register(register: Register){

  }
}
