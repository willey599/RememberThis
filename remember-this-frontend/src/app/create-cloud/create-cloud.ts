import { Component } from '@angular/core';

@Component({
  selector: 'app-create-cloud',
  imports: [],
  templateUrl: './create-cloud.html',
  styleUrl: './create-cloud.css'
})
export class CreateCloudComponent {
  cloudArray : number[] = [];

  nodeId: number | null = null;
  createCloud(){
    fetch("http://localhost:8080/api/create", {
      method: "GET",
      headers: {"Content-Type": "application/json"},
      credentials: "include"
    })
    .then(response => {
      if (this.nodeId == null){
        console.log("nodeId is null, backend controller is bugged");
      }
      else{
        this.cloudArray.push(this.nodeId);
        console.log("nodeId successfully pushed into cloudArray");
      }
    })
    .then(data =>{
      console.log(data);
    })
    .catch(err =>{
      console.log(err);
    })
    }
  }

