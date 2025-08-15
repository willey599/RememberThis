import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
    providedIn: "root"
})
export class CsrfInterceptor implements HttpInterceptor{
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        console.log("CsrfInterceptor class Intercept Called @@@@@@@@@");
        const csrfToken = this.getXsrfTokenFromCookie();
        if (csrfToken) {
            const clonedRequest = req.clone({
                headers: req.headers.set('X-CSRF-TOKEN', csrfToken)
            });
            return next.handle(clonedRequest);
        }
        return next.handle(req);''
    }

    getXsrfTokenFromCookie(): string {
        const xsrfToken = 'XSRF-TOKEN=';
        //decodeURIComponent undoes any encryption the browser might have done to the HTTP string
        const decodedCookie = decodeURIComponent(document.cookie);
        //split takes the big HTTP string and converts it to several array items
        const cookieArray = decodedCookie.split(";");
        //for every array item, checks to see if xsrftoken is found. If it's found, then extract string
        for (let i = 0; i < cookieArray.length; i++){
            //trims extra spaces
            let arrayItem = cookieArray[i].trim();
            if (arrayItem.indexOf(xsrfToken) === 0){
            return arrayItem.substring(xsrfToken.length, arrayItem.length);
            }
        }
        return '';
    }
}