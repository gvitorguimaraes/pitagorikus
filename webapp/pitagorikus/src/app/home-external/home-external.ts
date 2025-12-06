import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Topbar } from "../topbar/topbar";

@Component({
  selector: 'app-home-external',
  imports: [Topbar],
  templateUrl: './home-external.html',
  styleUrl: './home-external.css'
})
export class HomeExternal {
  
  constructor(private router: Router) {}

  goToRegister(){

    this.router.navigate(['login'], { queryParams: { register: true } });
  }
}
