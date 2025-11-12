import { Component, Signal, signal, WritableSignal } from '@angular/core';
import { LoginStatusService } from './login-status-service';


@Component({
  selector: 'app-home',
  imports: [],
  template: `
    <h1 class="sign-in">
      <button (click)="googleSignIn()">Google Sign In</button>
    </h1>

    <h2 class="welcome-message">
      <p>Welcome to Remember This! Testing is one of the best ways to reinforce your memory. Use this app like a set of flash cards.</p>
      <p>Each cloud can have up to 3 context words that you can use as "hints" to contextualize your main subject.</p>
    </h2>

    <h2 class="login-status">Login Status: {{ loginStatus() }}</h2>
  `,
  styleUrl: './home.css'
})
export class Home {

  loginStatus: WritableSignal<string>;

  constructor(private loginStatusService:LoginStatusService){
    this.loginStatus = loginStatusService.loginStatus;
  }


  googleSignIn(){
    window.location.href='https://remember-this-app.app/oauth2/authorization/google'
  }
}
