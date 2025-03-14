import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IResponse } from '../interfaces';
import { Injectable, inject } from '@angular/core';

/**
 * Generic base service for handling CRUD operations in an Angular application.
 * This service provides reusable HTTP methods for interacting with RESTful APIs.
 * 
 * @template T - Represents the type of data the service handles.
 */
@Injectable({
  providedIn: 'root',
})
export class BaseService<T> {
  /** The API endpoint for the resource. */
  protected source!: string;

  /** Injected HttpClient for making HTTP requests. */
  protected http = inject(HttpClient);

  /**
   * Retrieves a single entity by its ID.
   * @param id - The ID of the entity to retrieve.
   * @returns An observable containing the API response with the requested entity.
   */
  public find(id: string | number): Observable<IResponse<T>> {
    return this.http.get<IResponse<T>>(this.source + '/' + id);
  }

  /**
   * Retrieves all entities from the API.
   * @returns An observable containing the API response with a list of all entities.
   */
  public findAll(): Observable<IResponse<T[]>> {
    return this.http.get<IResponse<T[]>>(this.source);
  }

  /**
   * Retrieves all entities with optional query parameters.
   * @param params - Object containing query parameters to filter the results.
   * @returns An observable containing the API response with filtered results.
   */
  public findAllWithParams(params: any = {}): Observable<IResponse<T[]>> {
    return this.http.get<IResponse<T[]>>(this.source, { params: this.buildUrlParams(params) });
  }

  /**
   * Retrieves entities from a custom API endpoint with optional query parameters.
   * @param customUrlSource - Custom API endpoint suffix.
   * @param params - Query parameters for filtering.
   * @returns An observable containing the API response.
   */
  public findAllWithParamsAndCustomSource(customUrlSource: string, params: any = {}): Observable<IResponse<T[]>> {
    return this.http.get<IResponse<T[]>>(`${this.source}/${customUrlSource}`, { params: this.buildUrlParams(params) });
  }

  /**
   * Sends a POST request to add a new entity.
   * @param data - The data of the entity to be added.
   * @returns An observable containing the API response with the created entity.
   */
  public add(data: {}): Observable<IResponse<T>> {
    return this.http.post<IResponse<T>>(this.source, data);
  }

  /**
   * Sends a POST request to add an entity with additional query parameters.
   * @param params - Query parameters to be included in the request.
   * @param data - The data of the entity to be added.
   * @returns An observable containing the API response.
   */
  public addWithParams(params: any = {}, data: {}): Observable<IResponse<T>> {
    return this.http.post<IResponse<T>>(this.source, data, { params: this.buildUrlParams(params) });
  }

  /**
   * Sends a POST request to add an entity to a custom API endpoint.
   * @param customUrlSource - Custom API endpoint suffix.
   * @param data - The data of the entity to be added.
   * @returns An observable containing the API response.
   */
  public addCustomSource(customUrlSource: string, data: {}): Observable<IResponse<T>> {
    return this.http.post<IResponse<T>>(`${this.source}/${customUrlSource}`, data);
  }

  /**
   * Sends a PUT request to update an existing entity by ID.
   * @param id - The ID of the entity to update.
   * @param data - The updated data.
   * @returns An observable containing the API response with the updated entity.
   */
  public edit(id: number | undefined, data: {}): Observable<IResponse<T>> {
    return this.http.put<IResponse<T>>(this.source + '/' + id, data);
  }

  /**
   * Sends a PUT request to update an entity at a custom API endpoint.
   * @param customUrlSource - Custom API endpoint suffix.
   * @param data - The updated data.
   * @returns An observable containing the API response.
   */
  public editCustomSource(customUrlSource: string, data: {}): Observable<IResponse<T>> {
    return this.http.put<IResponse<T>>(`${this.source}${customUrlSource ? '/' + customUrlSource : ''}`, data);
  }

  /**
   * Sends a DELETE request to remove an entity by its ID.
   * @param id - The ID of the entity to delete.
   * @returns An observable containing the API response.
   */
  public del(id: any): Observable<IResponse<T>> {
    return this.http.delete<IResponse<T>>(this.source + '/' + id);
  }

  /**
   * Sends a DELETE request to remove an entity at a custom API endpoint.
   * @param customUrlSource - Custom API endpoint suffix.
   * @returns An observable containing the API response.
   */
  public delCustomSource(customUrlSource: string): Observable<IResponse<T>> {
    return this.http.delete<IResponse<T>>(`${this.source}/${customUrlSource}`);
  }

  /**
   * Converts an object of query parameters into an instance of HttpParams.
   * @param params - An object containing key-value pairs to be converted.
   * @returns An instance of HttpParams containing the formatted parameters.
   */
  public buildUrlParams(params: any = {}): HttpParams {
    let queryParams = new HttpParams();
    Object.keys(params).forEach(key => {
      queryParams = queryParams.append(key, params[key]);
    });
    return queryParams;
  }
}