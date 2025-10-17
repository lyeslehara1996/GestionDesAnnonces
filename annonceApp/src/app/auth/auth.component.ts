// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { AuthService } from 'src/services/auth.service';

// @Component({
//   selector: 'app-auth',
//   templateUrl: './auth.component.html',
//   styleUrls: ['./auth.component.scss']
// })
// export class AuthComponent implements OnInit {

//     errorMessage = '';
//   loading = false;

//   form = this.fb.group({
//     identifier: ['', Validators.required],
//     password: ['', Validators.required],
//   });


//   constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) { }

//   ngOnInit(): void {
//   }

//   login(): void {
//     if (this.form.invalid) return;

//     this.loading = true;
//     this.errorMessage = '';

//     const { identifier, password } = this.form.value;

//     this.auth.login(identifier!, password!).subscribe({
//       next: () => {
//         this.loading = false;
//         this.router.navigate(['/annonces']); 
//       },
//       error: (err) => {
//         this.loading = false;
//         this.errorMessage = 'Identifiants invalides. Veuillez réessayer.';
//         console.error(err);
//       }
//     });
//   }
// }
