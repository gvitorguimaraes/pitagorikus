import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Group } from "./model/Group";
import { Post } from "./model/Post";


@Injectable({
  providedIn: 'root'
})
export class GroupsService {

  constructor(private http: HttpClient){
  }

  public getGroups(): Group[]{

    //
    // mock
    let group1 = new Group();
    group1.id = 1;
    group1.name = 'Grupo dos nerdolas';
    group1.description = 'Esse grupo foi criado para ser um teste da tela';
    group1.groupId = 'ABCDEF';
    group1.urlPhoto = 'http';

    let group2 = new Group();
    group2.id = 2;
    group2.name = 'Grupo dos teste teste';
    group2.description = 'Esse grupo foi criado para ser um teste da tela 222 grupo 2';
    group2.groupId = 'FEDCBA';
    group2.urlPhoto = 'http';

    let groupsMock = [group1, group2];

    return groupsMock;
  }

  public getGroupPosts(group: Group): Post[]{

    

    return [];
  }
}