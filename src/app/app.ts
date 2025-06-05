import { Component } from '@angular/core';
import { CloudForm } from './cloud-form/cloud-form';


@Component({
  selector: 'app-root',
  imports: [CloudForm],
  template: `
  <header class="main-header">Main header</header>
  <h1>
    <app-cloud-form></app-cloud-form>
  </h1>`,
  styleUrl: './app.css',
})

export class App {
  protected title = 'RememberThis';
}
