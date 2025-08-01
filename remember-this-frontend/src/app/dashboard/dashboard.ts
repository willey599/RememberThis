import { Component, Signal } from '@angular/core';
import { CloudService } from "../cloud-service/cloud.service";
import { Cloud, CloudData } from '../cloud/cloud';
import { CommonModule } from '@angular/common';
import { CreateButton } from "../create-cloud-button/create-button/create-button";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  imports: [Cloud, CreateButton, CommonModule],
  template: `
    <h1 class="buttons">
      <button (click)="googleLogout()">Logout</button>
      <app-create-button></app-create-button>  
    
    </h1>
  <h2>
    <div class = "canvas">
      <!-- If value changes, continue -->
      <ul *ngIf="serviceCloudDataArray().length > 0">
        <h1 *ngFor="let cloudData of serviceCloudDataArray()">
            <app-cloud 
                [nodeId]="cloudData.nodeId"
                [nodeText]="cloudData.nodeText"
                [nodeXPosition]="cloudData.nodeXPosition"
                [nodeYPosition]="cloudData.nodeYPosition"
                [style.left.px]="cloudData.nodeXPosition"
                [style.top.px]="cloudData.nodeYPosition"
                >
            </app-cloud>
        </h1>
      </ul>
    </div>
  </h2>
  `,
  styles: [`
    .canvas {
      border: 3px solid;
      position: relative;
      width: 1080px;
      height: 720px;
      margin: 20px auto;
    }
    .buttons {
      text-align: center;
    }
    `]
})
export class Dashboard {
  serviceCloudDataArray: Signal<CloudData[]>;

  constructor(private cloudService : CloudService){
    this.serviceCloudDataArray = this.cloudService.cloudDataSignalArray;
  }

  ngOnInit(){
    this.cloudService.getInitialData();
  }
    
  googleLogout(){
    console.log("redirecting to Google logout endpoint");
    window.location.href = 'http://localhost:8080/logout';
    localStorage.removeItem('user_settings');
  }
}
