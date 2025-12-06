import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import { Groups } from "../groups/groups";
import { Invites } from '../invites/invites';
import { Profile } from "../profile/profile";

@Component({
  selector: 'app-home-intern',
  imports: [MatSidenavModule, MatButtonModule, MatIconModule, Groups, Invites, Profile],
  templateUrl: './home-intern.html',
  styleUrl: './home-intern.css'
})
export class HomeIntern {

  groupsActive: boolean = true;
  invitesActive: boolean = false;
  profileActive: boolean = false;

  
  changeToGroups(): void{
    this.groupsActive = true;
    this.invitesActive = false;
    this.profileActive = false;
  }

  changeToInvites(): void{
    this.invitesActive = true;
    this.groupsActive = false;
    this.profileActive = false;
  }

  changeToProfile(): void{
    this.profileActive = true;
    this.invitesActive = false;
    this.groupsActive = false;
  }
}
