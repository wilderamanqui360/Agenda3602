/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-05-31 at 05:52:27 UTC 
 * Modify at your own risk.
 */

package com.example.reapro.myapplication.backend.dominio.alumnoApi.model;

/**
 * Model definition for Alumno.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the alumnoApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Alumno extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String apellido;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Aula aula;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String blobImage;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Familiar> familiares;

  static {
    // hack to force ProGuard to consider Familiar used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Familiar.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fechanacimiento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nombre;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sexo;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getApellido() {
    return apellido;
  }

  /**
   * @param apellido apellido or {@code null} for none
   */
  public Alumno setApellido(java.lang.String apellido) {
    this.apellido = apellido;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Aula getAula() {
    return aula;
  }

  /**
   * @param aula aula or {@code null} for none
   */
  public Alumno setAula(Aula aula) {
    this.aula = aula;
    return this;
  }

  /**
   * @see #decodeBlobImage()
   * @return value or {@code null} for none
   */
  public java.lang.String getBlobImage() {
    return blobImage;
  }

  /**

   * @see #getBlobImage()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeBlobImage() {
    return com.google.api.client.util.Base64.decodeBase64(blobImage);
  }

  /**
   * @see #encodeBlobImage()
   * @param blobImage blobImage or {@code null} for none
   */
  public Alumno setBlobImage(java.lang.String blobImage) {
    this.blobImage = blobImage;
    return this;
  }

  /**

   * @see #setBlobImage()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public Alumno encodeBlobImage(byte[] blobImage) {
    this.blobImage = com.google.api.client.util.Base64.encodeBase64URLSafeString(blobImage);
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Familiar> getFamiliares() {
    return familiares;
  }

  /**
   * @param familiares familiares or {@code null} for none
   */
  public Alumno setFamiliares(java.util.List<Familiar> familiares) {
    this.familiares = familiares;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFechanacimiento() {
    return fechanacimiento;
  }

  /**
   * @param fechanacimiento fechanacimiento or {@code null} for none
   */
  public Alumno setFechanacimiento(java.lang.String fechanacimiento) {
    this.fechanacimiento = fechanacimiento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Alumno setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNombre() {
    return nombre;
  }

  /**
   * @param nombre nombre or {@code null} for none
   */
  public Alumno setNombre(java.lang.String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSexo() {
    return sexo;
  }

  /**
   * @param sexo sexo or {@code null} for none
   */
  public Alumno setSexo(java.lang.String sexo) {
    this.sexo = sexo;
    return this;
  }

  @Override
  public Alumno set(String fieldName, Object value) {
    return (Alumno) super.set(fieldName, value);
  }

  @Override
  public Alumno clone() {
    return (Alumno) super.clone();
  }

}
