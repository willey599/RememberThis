import { Component } from '@angular/core';
import { Cloud } from './cloud/cloud';


@Component({
  selector: 'app-root',
  imports: [Cloud],
  //template needs custom here because of app-cloud import from Cloud
  template: `
  <header class="main-header">Remember This</header>
  <h1>
  <button (click)="googleSignIn()">Google Sign In</button>  
  <app-cloud></app-cloud>
  <button (click)="googleLogout()">Logout</button>
  </h1>`,
  styleUrl: './app.css',
})

export class App {
  protected title = 'RememberThis';
  googleSignIn(){
    window.location.href='http://localhost:8080/oauth2/authorization/google'
  }
  googleLogout(){
    console.log("redirecting to Google logout endpoint");
    window.location.href = 'http://localhost:8080/logout';
    localStorage.removeItem('user_settings');
  }
}
