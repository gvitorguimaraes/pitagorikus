import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-topbar',
  imports: [],
  templateUrl: './topbar.html',
  styleUrl: './topbar.css'
})
export class Topbar {

  @Input() showLoginButton = false;

  constructor(private router: Router){
    
  }


  goToLogin(): void{

    this.router.navigate(['login']);
  }
}
