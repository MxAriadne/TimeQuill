import { Component } from '@angular/core';
import { QuillApiService} from '../quillapi/quillapi.service';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css'
})
export class ProjectListComponent {

  projects: any;

  constructor(private api: QuillApiService) {
    this.api.getProjectList().subscribe(data => {
      this.projects = data;
    });
  }

}
