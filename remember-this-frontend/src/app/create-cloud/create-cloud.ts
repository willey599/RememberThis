import { Component } from '@angular/core';
import { CommonModule}  from '@angular/common';
import { Cloud, CloudData } from "../cloud/cloud";
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-create-cloud',
  imports: [CommonModule, Cloud],
  templateUrl: './create-cloud.html',
  styleUrl: './create-cloud.css'
})
export class CreateCloud {
  //this is an array of CloudData objects, NOT of the Cloud object. The difference is that the CloudData is a smaller and more hollow version of Cloud made only when the button is pressed. These properties are used in the html file to help the ngFor loop instantiate REAL versions of the Cloud object in the cloud.ts file, making use of @Input.
  cloudArray : CloudData[] = [];
  
  ngOnInit(){
    fetch("http://localhost:8080/api/initialize", {
      method: "GET",
      headers: {"Content-Type": "application/json"},
      credentials: "include"
      }
    ).then (response => {
      return response.json();
    }).then(parsedInitData => {
      console.log("parsedData is: ", parsedInitData);
      for (const cloud of parsedInitData ){
        const initCloudData : CloudData = {
          nodeId: cloud.nodeId,
          nodeText: cloud.nodeText,
          xCoordinate: cloud.xCoordinate,
          yCoordinate: cloud.yCoordinate,
      }
      this.cloudArray.push(initCloudData);
      }
    })
  }

  createCloudButton(){
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
        const cloudData : CloudData = {
          nodeId: parsedResponseData,
          nodeText: '',
          xCoordinate: 300,
          yCoordinate: 300,
        }
        this.cloudArray.push(cloudData);
        console.log("cloud object successfully pushed into cloudArray. Array value: ");
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

