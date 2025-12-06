import { Component } from '@angular/core';
import { Invite } from './model/Invite';

@Component({
  selector: 'app-invites',
  imports: [],
  templateUrl: './invites.html',
  styleUrl: './invites.css'
})
export class Invites {

  invites: Invite[];

  constructor(){
    this.invites = [new Invite(), new Invite()];
  }
}
