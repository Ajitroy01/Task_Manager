import { Component, OnInit } from '@angular/core';
import * as AOS from 'aos';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  ngOnInit(): void {
    AOS.init({
      offset: 120,
      delay: 500,
      duration: 400,
      easing: 'ease',
      once: true,
      anchorPlacement: 'top-bottom',
    });
  }
}
