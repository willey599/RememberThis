import { Component } from '@angular/core';
import { CreateCloud } from "../create-cloud/create-cloud";

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
