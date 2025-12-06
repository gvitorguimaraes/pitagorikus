import { Component, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Topbar } from "../topbar/topbar";
import { Auth } from './model/Auth';
import { Register } from './model/Register';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, Topbar, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  authObj = signal(new Auth());
  registerObj = signal(new Register())

  registerView:boolean = false;

  constructor(private router: Router, 
              private route: ActivatedRoute,
              private service: LoginService) {

    this.route.queryParams.subscribe(q => {
      this.registerView = q['register'] === 'true';
    });

    
  }

  changeToRegister(): void{
    this.registerView = true;
  }

  changeToLogin(): void{
    this.registerView = false;
  }

  public login(){


    this.service.login(this.authObj());
  }

  public register(){


    this.service.register(this.registerObj())
  }
}
