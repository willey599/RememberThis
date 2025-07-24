import { Component } from '@angular/core';
import { CreateCloud } from "../cloud-service/cloud.service";

@Component({
  selector: 'app-dashboard',
  imports: [CreateCloud],
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
