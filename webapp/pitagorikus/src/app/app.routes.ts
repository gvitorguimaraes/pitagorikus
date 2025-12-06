import { Routes } from '@angular/router';
import { HomeExternal } from './home-external/home-external';
import { Login } from './login/login';
import { HomeIntern } from './home-intern/home-intern';

export const routes: Routes = [
    {path:"", component:HomeExternal},
    {path:"login", component:Login},
    {path:"home", component:HomeIntern}
];
