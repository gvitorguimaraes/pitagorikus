import { Component } from '@angular/core';
import { FormBuilder, FormGroup,ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  imports: [ReactiveFormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

  editMode = false;

  user = {
    name: 'João Silva',
    email: 'joao.silva@example.com',
    bio: 'Desenvolvedor Angular apaixonado por frontend.',
    avatar: 'https://via.placeholder.com/150'
  };

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      name: [this.user.name],
      email: [this.user.email],
      bio: [this.user.bio]
    });
  }

  toggleEdit() {
    this.editMode = !this.editMode;

    if (!this.editMode) {
      this.user = { ...this.user, ...this.form.value };
    }
  }
}
