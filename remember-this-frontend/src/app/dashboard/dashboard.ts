import { Component, Signal } from '@angular/core';
import { CloudService } from "../cloud-service/cloud.service";
import { Cloud, CloudData } from '../cloud/cloud';
import { CommonModule } from '@angular/common';
import { CreateButton } from "../create-cloud-button/create-button/create-button";

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
                [nodeContext1]="cloudData.nodeContext1"
                [nodeContext2]="cloudData.nodeContext2"
                [nodeContext3]="cloudData.nodeContext3"
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
      position: relative;
      width: 1920px;
      height: 1080px;
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
  ngOnDestroy(){
    try{
      console.log("clearing cloudDataSignalArray");
      this.cloudService.clearCloudArray();
    }catch (error){
      console.log("error clearing cloudDataSignalArray in ngOnDestroy: ", error);
    }
  }
    
  googleLogout(){
    console.log("redirecting to Google logout endpoint");
    window.location.href = 'https://remember-this-app.app/logout';
    localStorage.removeItem('user_settings');
  }
}
