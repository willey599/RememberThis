import { Component, Injectable, Signal, signal, WritableSignal } from '@angular/core';
import { CommonModule}  from '@angular/common';
import { Cloud, CloudData } from "../cloud/cloud";
import { OnInit } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

//this function takes the current document's cookies and parses them for the XSRF token
function getXsrfTokenFromCookie(): string {
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
@Injectable({
  providedIn: 'root'
})



export class CloudService {
  //this is this class's array of CloudData objects, NOT of the Cloud object. The difference is that the CloudData is a smaller and more hollow version of Cloud made only when the button is pressed. These properties are used in the html file to help the ngFor loop instantiate REAL versions of the Cloud object in the cloud.ts file, making use of @Input.
  
  //create subscribable object for use in other components

  // public cloudArray$: Observable<CloudData[]> = this._cloudArray.asObservable();
  private _cloudDataSignalArray : WritableSignal<CloudData[]> = signal([]);
  public cloudDataSignalArray : Signal<CloudData[]> = this._cloudDataSignalArray.asReadonly();
  constructor(){
    
  }

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

      for (const cloud of parsedInitData ){
        const initCloudData : CloudData = {
          nodeId: signal(cloud.nodeId),
          nodeText: signal(cloud.nodeText),
          nodeContext1: signal(cloud.nodeContext1),
          nodeContext2: signal(cloud.nodeContext2),
          nodeContext3: signal(cloud.nodeContext3),
          nodeXPosition: cloud.nodeXPosition,
          nodeYPosition: cloud.nodeYPosition,
        }

        this._cloudDataSignalArray.update(currentArray => [...currentArray, initCloudData]);
      }
    })
  }

  deleteCloud(returnedNodeId: number){
      console.log("deleteClicked returned true, activating fetch delete request");
      //grab xsrfToken
      const xsrfToken = getXsrfTokenFromCookie();

      console.log("extracted XSRF token from document cookies");
      const nodeId : number = returnedNodeId;
      fetch(`http://localhost:8080/api/delete`, {
        method: "DELETE",
        body: JSON.stringify({ 
          nodeId: returnedNodeId
        }),
        headers: { 
          "Content-Type": "application/json",
          "X-XSRF-TOKEN": xsrfToken
         },
        credentials: "include"
      }).then(response => {
        if (response.ok){
          //first update function separates original signal array into individual elements
          //then filter takes currentArray and separates into arrayItems
          //then arrayItem.nodeId finds that element's nodeId
          this._cloudDataSignalArray.update(currentArray => currentArray.filter(arrayItem => arrayItem.nodeId() !== returnedNodeId));
          //syntax: item => item.property => checkCondition
          console.log("found node in _cloudArray, successfully deleted node");
        }
        else{
          console.log("Server error:", response.status);  
          }
        }).then(data => console.log(data))
      .catch(err => console.error(err));
    }

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
    .then(parsedNodeId => {
      console.log("parsed data: " + parsedNodeId);
      console.log("type of parsedResponseData: " + typeof parsedNodeId);
      if (parsedNodeId == null || typeof parsedNodeId != 'number'){
        console.log("nodeId is null, backend controller is bugged");
      }
      else{
        let nodeTextSignal = signal('');
        let nodeIdSignal = signal(parsedNodeId);
        let nodeContext1Signal = signal('');
        let nodeContext2Signal = signal('');
        let nodeContext3Signal = signal('');
        let nodeXPosition = 0;
        let nodeYPosition = 0;

        const cloudData : CloudData = {
          nodeId: nodeIdSignal,
          nodeText: nodeTextSignal,
          nodeContext1: nodeContext1Signal,
          nodeContext2: nodeContext2Signal,
          nodeContext3: nodeContext3Signal,
          nodeXPosition: nodeXPosition,
          nodeYPosition: nodeYPosition,
        }
        

        //update Signal Array
        //syntax: update loops through _cloudDataSignalArray via current array
        //spread operator spreads current array into individual array alements
        //adds cloudData to the end of it
        this._cloudDataSignalArray.update(currentArray => [...currentArray, cloudData]);
        console.log("cloud object successfully pushed into cloudArray.");
      }
    })
    .catch(err =>{
      console.log(err);
    })
  }
  
  saveCloud(recallArray: string[], nodeId: number){
    const xsrfToken = getXsrfTokenFromCookie();

    fetch("http://localhost:8080/api/save", {
            method: "POST",
            body: JSON.stringify({ 
            nodeText: recallArray[0],
            nodeContext1: recallArray[1],
            nodeContext2: recallArray[2],
            nodeContext3: recallArray[3],
            nodeId: nodeId,
            }),
            headers: { "Content-Type": "application/json",
              "X-XSRF-TOKEN": xsrfToken
             },
            credentials: "include"     // Important to send cookies or auth info if backend expects it
          })
          .then(response => {
            if(response.ok){
              console.log("successful data transfer");
            }
            else {
              console.log("Server error:", response.status)  
              }
            })
          .then(data => console.log(data))
          .catch(err => console.error(err));
  }

  savePosition(nodeXPosition: number, nodeYPosition: number, nodeId: number){
    const xsrfToken = getXsrfTokenFromCookie();
    console.log("position data: ", nodeXPosition, nodeYPosition);
    fetch("http://localhost:8080/api/savePosition", {
            method: "POST",
            body: JSON.stringify({ 
              nodeId: nodeId,
              nodeXPosition: nodeXPosition,
              nodeYPosition: nodeYPosition,
            }),
            headers: { "Content-Type": "application/json", 
              "X-XSRF-TOKEN": xsrfToken
             },
            credentials: "include"     // Important to send cookies or auth info if backend expects it
          })
          .then(response => {
            if(response.ok){
              console.log("successful data transfer");
            }
            else {
              console.log("Server error:", response.status)  
              }
            })
          .then(data => console.log(data))
          .catch(err => console.error(err));
  }

  clearCloudArray(){
    console.log("Clearing _cloudDataSignalArray");
    this._cloudDataSignalArray.set([]);
  }
}
  

