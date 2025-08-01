import { Component, Inject, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatButton } from '@angular/material/button';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatDialogTitle } from '@angular/material/dialog';
import { Cloud } from '../cloud';
import { DialogRef } from '@angular/cdk/dialog';

@Component({
  selector: 'app-cloud-create-menu',
  imports: [MatDialogTitle, MatButton, MatFormField, MatInputModule, FormsModule],
  templateUrl: './cloud-create-menu.html',
  styleUrl: './cloud-create-menu.css'
})
export class CloudCreateMenu {
  recallText: string = '';
  nodeContext1: string = '';
  nodeContext2: string = '';
  nodeContext3: string = '';
  readonly dialogRef = inject(MatDialogRef<CloudCreateMenu>);
  //contains data from cloud.ts
  readonly data = inject(MAT_DIALOG_DATA);

  
  //@Inject and inject() are very similar, but @Inject(token) is supposed to be used inside a constructor parameter to inject. inject() avoids the need to use @Inject.
  //MAT_DIALOG_DATA is a token that keeps track of data throughout the dialog lifecycle. It's passed in by the dialog.open() function in cloud.ts and carries with it any specified data that it's passed in with in the function (in this case- name: this.name). That data is now in MAT_DIALOG_DATA
  //data: any is holding all the data that was passed in from dialog.open() through dependency injection. That data can be accessed like data.name. "public" lets you use data.name in your html
  

  saveDialog(mouseEvent: MouseEvent, recallItem: any): void{
    if (mouseEvent.button == 0){
      console.log("closing dialog box.");
      if (this.recallText){
        const recallArray = [
          this.nodeContext1, this.nodeContext2, this.nodeContext3
        ]
        this.dialogRef.close({recallArray});
      }
    }
  }

  cancelDialog(mouseEvent: MouseEvent): void{
    if (mouseEvent.button == 0){
      this.dialogRef.close();
    }
  }

  deleteButton(mouseEvent: MouseEvent){
    if (mouseEvent.button == 0){
      console.log("delete button clicked");
      this.dialogRef.close(true);
    }
  }
}
