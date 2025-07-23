import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCloud } from './create-cloud';

describe('CreateCloudComponent', () => {
  let component: CreateCloud;
  let fixture: ComponentFixture<CreateCloud>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateCloud]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateCloud);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
