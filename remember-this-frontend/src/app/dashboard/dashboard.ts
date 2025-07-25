import { Component } from '@angular/core';
import { CloudService } from "../cloud-service/cloud.service";
import { Cloud, CloudData } from '../cloud/cloud';
import { CommonModule } from '@angular/common';
import { CreateButton } from "../create-cloud-button/create-button/create-button";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  imports: [Cloud, CreateButton, CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {

  cloudArray$ = new Observable<CloudData[]>;
  constructor(private cloudService : CloudService){
    this.cloudArray$ = cloudService.cloudArray$;
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
