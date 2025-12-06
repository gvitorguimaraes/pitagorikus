import {ChangeDetectionStrategy, Component } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import { Group } from './model/Group';
import { Post } from './model/Post';
import { GroupsService } from './groups.service';

@Component({
  selector: 'app-groups',
  imports: [MatCardModule, MatGridListModule, MatButtonModule],
  templateUrl: './groups.html',
  styleUrl: './groups.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Groups {

  //
  // view group list
  groups:Group[];

  //
  // view group selected
  groupActiveView: Group | null = null;
  groupPosts:Post[] = [new Post(), new Post()];

  constructor(private service: GroupsService){
    this.groups = service.getGroups();
  }

  public selectViewGroup(group: Group): void{
    this.groupActiveView = group;
  }
}
