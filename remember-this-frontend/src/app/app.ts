import { Component } from '@angular/core';
import { Cloud } from './cloud/cloud';
import { CreateCloud } from "./cloud-service/cloud.service";
import { RouterOutlet, RouterLink } from '@angular/router';


@Component({
  standalone: true,
  selector: 'app-root',
  imports: [CreateCloud, RouterOutlet, RouterLink],
  //template needs custom here because of app-cloud import from Cloud
  template: `
  <header class="main-header">
    <h1>Remember This</h1>
    <h2>
      <nav>
    <a routerLink="/">Home</a> |
    <a routerLink="/dashboard">Dashboard</a>
  </nav></h2>
  </header>
  
  <router-outlet></router-outlet>`,
  styleUrl: './app.css',
})

export class App {
  protected title = 'RememberThis';
  
}
