import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Annonce {
  id?:number;
  title: string;
  description?:string;
  prix:number;
  categorie:string;
  dateModifiaction?:string;
  auteur:string;
  email?:string;
  telephone?:string;
  active?:boolean;

}

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {
private ApiUrl= "http://localhost:8085/api/annonces";


  constructor(private http:HttpClient) { }

 
  getAll(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(this.ApiUrl);
  }

  getById(id: number): Observable<Annonce> {
  return this.http.get<Annonce>(`${this.ApiUrl}/${id}`);
  }

  create(annonce: Annonce): Observable<Annonce> {
    return this.http.post<Annonce>(this.ApiUrl, annonce);
  }

  update(id: number, annonce: Annonce): Observable<Annonce> {
    return this.http.put<Annonce>(`${this.ApiUrl}/${id}`, annonce);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.ApiUrl}/${id}`);
  }

  search(filters: any, page = 0, size = 10, sort = 'dateCreation', direction = 'desc'): Observable<any> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort)
      .set('direction', direction);

    Object.keys(filters).forEach(key => {
      if (filters[key] !== null && filters[key] !== undefined && filters[key] !== '') {
        params = params.set(key, filters[key]);
      }
    });

    return this.http.get<any>(`${this.ApiUrl}/search`, { params });
  }
}


