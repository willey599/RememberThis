import { Component, Injectable } from '@angular/core';
import { CommonModule}  from '@angular/common';
import { Cloud, CloudData } from "../cloud/cloud";
import { OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class CloudService {
  //this is this class's array of CloudData objects, NOT of the Cloud object. The difference is that the CloudData is a smaller and more hollow version of Cloud made only when the button is pressed. These properties are used in the html file to help the ngFor loop instantiate REAL versions of the Cloud object in the cloud.ts file, making use of @Input.
  private _cloudArray = new BehaviorSubject<CloudData[]>([]);
  //create subscribable object for use in other components
  public cloudArray$: Observable<CloudData[]> = this._cloudArray.asObservable();

  
  
  getInitialData(){
    fetch("http://localhost:8080/api/initialize", {
      method: "GET",
      headers: {"Content-Type": "application/json"},
      credentials: "include"
      }
    ).then (response => {
      return response.json();
    }).then(parsedInitData => {
      console.log("parsedData is: ", parsedInitData);

      const currentArray = this._cloudArray.getValue();
      const newArray = [...currentArray];
      
      for (const cloud of parsedInitData ){
        const initCloudData : CloudData = {
          nodeId: cloud.nodeId,
          nodeText: cloud.nodeText,
          xCoordinate: cloud.xCoordinate,
          yCoordinate: cloud.yCoordinate,
        }
        newArray.push(initCloudData);
      }
      //update reactable array
      this._cloudArray.next(newArray);
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
        

        //grab current array (it's reactive)
        const currentArray = this._cloudArray.getValue();
        //create new array with currentArray and include new item manually
        const newArray = [...currentArray, cloudData];
        //tell subscribers about update
        this._cloudArray.next(newArray);
        console.log("cloud object successfully pushed into cloudArray. Current array: " + newArray);
      }
    })
    .catch(err =>{
      console.log(err);
    })
    }
  }

