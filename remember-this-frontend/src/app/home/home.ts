import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  googleSignIn(){
    window.location.href='http://localhost:8080/oauth2/authorization/google'
  }
}
