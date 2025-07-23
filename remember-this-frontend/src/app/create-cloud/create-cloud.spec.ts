import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCloudComponent } from './create-cloud';

describe('CreateCloudComponent', () => {
  let component: CreateCloudComponent;
  let fixture: ComponentFixture<CreateCloudComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateCloudComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCloudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
