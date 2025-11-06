import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {
  googleSignIn(){
    window.location.href='http://rememberthis.local/oauth2/authorization/google'
  }
}
