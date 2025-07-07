import { Component } from '@angular/core';
import { Cloud } from './cloud/cloud';


@Component({
  selector: 'app-root',
  imports: [Cloud],
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
