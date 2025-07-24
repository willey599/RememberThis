import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CloudService } from './cloud.service';

describe('CreateCloudComponent', () => {
  let component: CloudService;
  let fixture: ComponentFixture<CloudService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CloudService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CloudService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
