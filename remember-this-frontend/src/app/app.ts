import { Component } from '@angular/core';
import { Cloud } from './cloud/cloud';
import { CreateCloud } from "./create-cloud/create-cloud";
import { RouterOutlet, RouterLink } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-root',
  imports: [CreateCloud, RouterOutlet, RouterLink],
  //template needs custom here because of app-cloud import from Cloud
  template: `
  <header class="main-header">Remember This</header>
  <h1>
  <nav>
    <a routerLink="/">Home</a> |
    <a routerLink="/dashboard">Dashboard</a>
  </nav>
  <button (click)="googleSignIn()">Google Sign In</button>
  <app-create-cloud></app-create-cloud>
  <button (click)="googleLogout()">Logout</button>
  </h1>
  <router-outlet></router-outlet>`,
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
