import { Component } from '@angular/core';
import { CreateCloud } from "../cloud-service/cloud.service";

@Component({
  selector: 'app-home',
  imports: [CreateCloud],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  googleSignIn(){
    window.location.href='http://localhost:8080/oauth2/authorization/google'
  }
}
