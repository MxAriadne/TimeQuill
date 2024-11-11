import { Routes } from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import {HomeComponent} from './home-component/home-component.component';

export const routes: Routes = [
  { path: 'projects', component: ProjectListComponent },
  { path: '', component: HomeComponent },
];
