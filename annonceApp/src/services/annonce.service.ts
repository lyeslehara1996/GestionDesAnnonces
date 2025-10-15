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

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // numéro de page
  size: number;
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

  searchAnnonces(
  page: number,
  size: number,
  sort: string,
  direction: string,
  filters?: { prixMin?: number; prixMax?: number; title?: string; categories?: string[] }
): Observable<Page<Annonce>> {
  let params = new HttpParams()
    .set('page', page)
    .set('size', size)
    .set('sort', sort)
    .set('direction', direction);

  if (filters?.prixMin) params = params.set('prixMin', filters.prixMin);
  if (filters?.prixMax) params = params.set('prixMax', filters.prixMax);
  if (filters?.title) params = params.set('title', filters.title); // ✅ corriger ici
  if (filters?.categories && filters.categories.length > 0) {
    filters.categories.forEach(c => params = params.append('categories', c));
  }

  return this.http.get<Page<Annonce>>(`${this.ApiUrl}/search`, { params });
}


}


