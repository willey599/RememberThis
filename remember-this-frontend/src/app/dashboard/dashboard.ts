import { Component } from '@angular/core';
import { CloudService } from "../cloud-service/cloud.service";
import { Cloud } from '../cloud/cloud';
import { CommonModule } from '@angular/common';
import { CreateButton } from "../create-cloud-button/create-button/create-button";

@Component({
  selector: 'app-dashboard',
  imports: [Cloud, CreateButton],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {
  googleLogout(){
    console.log("redirecting to Google logout endpoint");
    window.location.href = 'http://localhost:8080/logout';
    localStorage.removeItem('user_settings');
  }
  
}
