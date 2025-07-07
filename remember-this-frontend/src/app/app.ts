import { Component } from '@angular/core';
import { Cloud } from './cloud/cloud';
import { LoginComponent } from "./cloud/social-login/login.component";


@Component({
  selector: 'app-root',
  imports: [Cloud, LoginComponent],
  //template needs custom here because of app-cloud import from Cloud
  template: `
  <header class="main-header">Remember This</header>
  <h1>
    <app-cloud></app-cloud>
    <app-cloud></app-cloud>
  </h1>`,
  styleUrl: './app.css',
})

export class App {
  protected title = 'RememberThis';
}
