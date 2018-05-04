import { Component, OnInit, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { AuthenticationService } from '../shared/technical/authentication.service';
import { User } from '../shared/models/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  menuOpened = false;

  constructor(private authenticationService : AuthenticationService) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.menuOpened = true;
    }, 400);
  }

  logout() {
    this.authenticationService.logout();
  }

  get currentUser(): User {
    return this.authenticationService.currentUser;
  }

  toggleMenu() {
    this.menuOpened = !this.menuOpened;
  }

  menuClosed() {
    this.menuOpened = false;
  }
}
