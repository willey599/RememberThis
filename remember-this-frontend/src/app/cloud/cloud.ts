import { Component, inject, model, Input } from '@angular/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatButton } from '@angular/material/button';
import { CloudCreateMenu } from './cloud-create-menu/cloud-create-menu';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';

export interface CloudData{
  nodeId : number;
  nodeText : string;
  xCoordinate : number;
  yCoordinate : number;
}

@Component({
  standalone: true,
  selector: 'app-cloud',
  imports: [DragDropModule, MatDialogModule, CloudCreateMenu],
  templateUrl: './cloud.html',
  styleUrl: './cloud.css'
})

export class Cloud implements CloudData{
  mouseDownX : Number | null = null;
  mouseDownY : Number | null = null;
  mouseUpX : Number | null = null;
  mouseUpY : Number | null = null;
  public userText = '';
  @Input() nodeId! : number;
  @Input() xCoordinate! : number;
  @Input() yCoordinate! : number;
  @Input() nodeText! : string;


  //dependency injection, create handle for MatDialog object
  readonly dialog = inject(MatDialog);

  onMouseDown(mouseState: MouseEvent): void{
    this.mouseDownX = mouseState.clientX;
    this.mouseDownY = mouseState.clientY;
  }

  //if mouseUp is in the same location as mouseDown, then dialog box should open
  onMouseUp(mouseState : MouseEvent): void {
    this.mouseUpX = mouseState.clientX;
    this.mouseUpY = mouseState.clientY;
    if (this.mouseDownX == this.mouseUpX && this.mouseDownY == this.mouseUpY){
      const dialogRef = this.dialog.open(CloudCreateMenu /*, {data: {name: this.name}}*/);
      
      dialogRef.afterClosed().subscribe(result => {
        if (result){
          console.log('Dialog closed, data: ', result);
          this.userText = result;
          //store data in DB using fetch
          fetch("http://localhost:8080/api/save", {
            method: "POST",
            body: JSON.stringify({ 
              nodeText: this.userText,
              nodeId: this.nodeId,
              
            }),
            headers: { "Content-Type": "application/json" },
            credentials: "include"     // Important to send cookies or auth info if backend expects it
          })
          .then(response => {
            if(response.ok){
              console.log("successful data transfer")}
            else {
              console.log("Server error:", response.status)  
              }
            })
          .then(data => console.log(data))
          .catch(err => console.error(err));
                  }
      });
    }
  }


}