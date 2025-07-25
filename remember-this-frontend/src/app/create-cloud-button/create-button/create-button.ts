import { Component } from '@angular/core';
import { CloudService } from '../../cloud-service/cloud.service';
import { Cloud, CloudData } from '../../cloud/cloud';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-create-button',
  imports: [Cloud, CommonModule],
  templateUrl: './create-button.html',
  styleUrl: './create-button.css'
})
export class CreateButton {    
    
    constructor(private cloudService: CloudService){
  }
  createButtonClicked(){
    this.cloudService.createCloud();
  }
}
