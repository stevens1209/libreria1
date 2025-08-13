import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Cliente } from '../../model/cliente.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ClienteService } from '../../services/cliente';
import Swal from 'sweetalert2';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-cliente',
  standalone: false,
  templateUrl: './cliente.html',
  styleUrl: './cliente.css'
})
export class ClienteComponent implements OnInit {

  clientes: Cliente[] = [];
  cliente: Cliente = {} as Cliente;
  editar: boolean = false;
  idEditar: number | null = null;

  dataSource!: MatTableDataSource<Cliente>;
  mostrarColumnas: String[] = ['idCliente', 'cedula', 'nombre', 'apellido', 'direccion', 'telefono', 'correo', 'acciones'];

  @ViewChild('formularioCliente') formularioCliente!: ElementRef;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private clienteService: ClienteService) { }


  ngOnInit(): void {
    this.findAll();//muestra la lista de clientes al iniciar la pagina web
  }

  findAll(): void {
    this.clienteService.findAll().subscribe(data => {
      //this.clientes = data asi deberia ser la forma simple
      this.dataSource = new MatTableDataSource(data); //asi usando angular material
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });

}

save(): void {

  this.clienteService.save(this.cliente).subscribe(() => {
    this.cliente = {} as Cliente; // Limpiar el formulario
    this.findAll(); // Actualizar la lista de clientes
  });
}

update(): void {

  if(this.idEditar !== null) {
    this.clienteService.update(this.idEditar, this.cliente).subscribe(() => {
      this.cliente = {} as Cliente; // Limpiar el formulario
      this.editar = false; // Cambiar a modo no edición
      this.idEditar = null; // Reiniciar el ID de edición
      this.findAll(); // Actualizar la lista de clientes
    });
  }
}

delete():void{
  Swal.fire({
    title: '¿Desea eliminar el dato?',
    text: "¡No podrá revertir esta acción!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar',
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33'
  }).then((result) => {
    if (result.isConfirmed){
      this.clienteService.delete(this.cliente.idCliente).subscribe(() => {
        this.findAll(); // Actualizar la lista de clientes
        this.cliente = {} as Cliente; // Limpiar el formulario
        Swal.fire('Eliminado', 'El cliente ha sido eliminado.', 'success');
      });
    }else{
      this.cliente = {} as Cliente; // Limpiar el formulario
    }
  });
}

//Metodos para interaccion en la pagina web 

editarCliente(cliente: Cliente): void {
  this.cliente = { ...cliente }; // Copiar los datos del cliente a editar
  this.idEditar = cliente.idCliente; // Guardar el ID del cliente a editar
  this.editar = true; // Cambiar a modo edición

  setTimeout(() => {
    this.formularioCliente.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
  },100); // Esperar un poco para que el formulario se muestre correctamente
}

editarClienteCancelar(form: NgForm): void {
  this.cliente = {} as Cliente; // Limpiar el formulario
  this.idEditar = null; // Reiniciar el ID de edición
  this.editar = false; // Cambiar a modo no edición
  form.resetForm(); // Limpiar el formulario
}

guardarCliente(form: NgForm): void {
  if (this.editar && this.idEditar !== null) {
    this.update(); // Actualizar cliente
  }else{
    this.save(); // Guardar nuevo cliente
    form.resetForm(); // Limpiar el formulario
  }
}

buscarCliente(event: Event){
  const filtro = (event.target as HTMLInputElement).value;
  this.dataSource.filter = filtro.trim().toLowerCase();
} 

}