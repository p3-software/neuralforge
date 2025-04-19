import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Learnspace } from '../interfaces';

@Injectable({ providedIn: 'root' })
export class LearnspaceService {
  private apiUrl = 'http://localhost:8080/api/learnspaces';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Learnspace[]> {
    return this.http.get<Learnspace[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  create(data: any): Observable<Learnspace> {
    return this.http.post<Learnspace>(this.apiUrl, data, { headers: this.getHeaders() });
  }

  private getHeaders() {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ 'Authorization': `Bearer ${token}` });
  }
}
