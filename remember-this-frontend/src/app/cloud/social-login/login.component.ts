import { SocialAuthServiceConfig, SocialLoginModule, GoogleLoginProvider, SocialAuthService, SocialUser } from '@abacritt/angularx-social-login';
//lets me use httpclientmodule
import { Component, importProvidersFrom } from '@angular/core';
import { NgIf } from '@angular/common';
@Component({
    standalone: true,
    selector: 'social-login',
    imports: [NgIf],
    template: `
    
    <div *ngIf="user"> Logged in! User: {{user.name}}, ID Token: {{user.idToken}}</div>
    `

})
export class LoginComponent{
    user: SocialUser | null = null;
    constructor(private authService: SocialAuthService) {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      console.log('Google user info:', user);
    });
  }

  signInWithGoogle(): void {
    this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }
 
  signOutFromGoogle(): void {
    this.authService.signOut();
  }
}
