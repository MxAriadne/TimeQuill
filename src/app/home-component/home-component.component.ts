import { Component } from '@angular/core';
import {QuillApiService} from '../quillapi/quillapi.service';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [],
  templateUrl: './home-component.component.html',
  styleUrl: './home-component.component.css'
})
export class HomeComponent {
  data: any;

  constructor(private api: QuillApiService) {
    this.api.getProjectList().subscribe(data => {
      this.data = data;
    });
  }
}
