import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-are-you-sure',
  templateUrl: './are-you-sure.component.html',
  styleUrls: ['./are-you-sure.component.css']
})
export class AreYouSureComponent implements OnInit {

  message: string = '';

  constructor(
    private dialogRef: MatDialogRef<AreYouSureComponent>,
    @Inject(MAT_DIALOG_DATA) data) {
      this.message = data.message;
  }

  ngOnInit() {
  }

  process() {
    this.dialogRef.close(true);
  }

  cancel() {
    this.dialogRef.close(false);
  }
}
