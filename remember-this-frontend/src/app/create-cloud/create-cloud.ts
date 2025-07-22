import { Component } from '@angular/core';

@Component({
  selector: 'app-create-cloud',
  imports: [],
  templateUrl: './create-cloud.html',
  styleUrl: './create-cloud.css'
})
export class CreateCloudComponent {
  cloudArray : number[] = [];
  
  createCloud(){
    fetch("http://localhost:8080/api/create", {
      method: "GET",
      headers: {"Content-Type": "application/json"},
      credentials: "include"
    })
    //convert HTTP response object to json
    .then(response => {
      //this returns a Json wrapped in a promise
      return response.json();
    })
    .then(parsedResponseData => {
      console.log("parsed data: " + parsedResponseData);
      console.log("type of parsedResponseData: " + typeof parsedResponseData);
      if (parsedResponseData == null || typeof parsedResponseData != 'number'){
        console.log("nodeId is null, backend controller is bugged");
      }
      else{
        this.cloudArray.push(parsedResponseData);
        console.log("nodeId successfully pushed into cloudArray. Array value: ");
        for (let i = 0; i < this.cloudArray.length; i++) {
          console.log(this.cloudArray[i]);
        }
      }
    })
    .catch(err =>{
      console.log(err);
    })
    }
  }

