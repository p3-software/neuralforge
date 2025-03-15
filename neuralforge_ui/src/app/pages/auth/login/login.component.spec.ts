import { TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginComponent } from './login.component';
import { AuthService } from '../../../services/auth.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('LoginComponent', () => {
  let component: LoginComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, HttpClientTestingModule],
      providers: [LoginComponent, AuthService, { provide: Router, useValue: {} }],
    });

    component = TestBed.inject(LoginComponent);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});